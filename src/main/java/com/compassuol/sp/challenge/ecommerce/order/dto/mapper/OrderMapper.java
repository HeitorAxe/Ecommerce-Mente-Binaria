package com.compassuol.sp.challenge.ecommerce.order.dto.mapper;

import com.compassuol.sp.challenge.ecommerce.order.consumer.ViaCepConsumerFeign;
import com.compassuol.sp.challenge.ecommerce.order.dto.*;
import com.compassuol.sp.challenge.ecommerce.order.entity.Address;
import com.compassuol.sp.challenge.ecommerce.order.entity.Order;
import com.compassuol.sp.challenge.ecommerce.order.enums.OrderStatus;
import com.compassuol.sp.challenge.ecommerce.order.enums.PaymentMethod;
import com.compassuol.sp.challenge.ecommerce.order.repository.AddressRepository;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class OrderMapper {
    ProductService productService;

    public static Order toOrder(OrderCreateDTO dto) {
        ModelMapper mapper = new ModelMapper();
        Order order = new Order();
        order.setAddress(mapper.map(dto.getAddress(), Address.class));
        order.setPaymentMethod(PaymentMethod.valueOf(dto.getPaymentMethod()));
        return order;
    }


    public static OrderResponseDTO toDTO(Order order) {
        ModelMapper mapper = new ModelMapper();
        OrderResponseDTO orderResponseDTO = mapper.map(order, OrderResponseDTO.class);
        Set<Product> uniqueProducts = new HashSet<>(order.getProducts());
        List<OrderHasProductDTO> orderHasProductDTOList = uniqueProducts.stream()
                .map(product -> new OrderHasProductDTO(product.getId(), countProductsInOrder(order, product)))
                .collect(Collectors.toList());
        orderResponseDTO.setProducts(orderHasProductDTOList);
        return orderResponseDTO;
    }
    private static int countProductsInOrder(Order order, Product product) {
        return (int) order.getProducts().stream().filter(p -> p.equals(product)).count();
    }

    public static List<OrderResponseDTO> toListDto(List<Order> orders) {
        return orders.stream().map(order -> toDTO(order)).collect(Collectors.toList());
    }
    public static void toAddress(AddressDTO dto, Address address) {
        if(dto.getCity()==null)
            address.setCity(dto.getCity());
        if(dto.getStreet()==null)
            address.setStreet(dto.getStreet());
        if(dto.getState()==null)
            address.setState(dto.getState());

        address.setNumber(dto.getNumber());
        address.setComplement(dto.getComplement());
        address.setPostalCode(dto.getPostalCode());
    }

    public static void updateOrder(Order order, OrderUpdateDTO orderDto,
                                   ProductRepository productRepository, AddressRepository addressRepository,
                                   ViaCepConsumerFeign viaCepConsumerFeign) {

        updateOrderDetails(order, orderDto);
        updateOrderProducts(order, orderDto.getProducts(), productRepository);
        updateOrderAddress(order, orderDto.getAddress(), addressRepository, viaCepConsumerFeign);
    }
    private static void updateOrderDetails(Order order, OrderUpdateDTO orderDto) {
        if (orderDto.getPaymentMethod() != null) {
            order.setPaymentMethod(PaymentMethod.valueOf(orderDto.getPaymentMethod()));
        }
        if (orderDto.getOrderStatus() != null) {
            order.setOrderStatus(OrderStatus.valueOf(orderDto.getOrderStatus()));
        }
    }

    private static void updateOrderProducts(Order order, List<OrderHasProductDTO> productDTOList, ProductRepository productRepository) {
        order.getProducts().clear();
        if (productDTOList != null) {
            for (OrderHasProductDTO orderProduct : productDTOList) {
                Product product = productRepository.findById(orderProduct.getProductId()).orElseThrow(
                        () -> new EntityNotFoundException(String.format("Product with id %s not found", orderProduct.getProductId()))
                );
                order.addProduct(product, orderProduct.getQuantity());
            }
        } else {
            System.out.println("Warning: productDTOList is null.");
        }
    }

    private static void updateOrderAddress(Order order, AddressDTO addressDTO, AddressRepository addressRepository, ViaCepConsumerFeign viaCepConsumerFeign) {
        if (addressDTO != null && addressDTO.getPostalCode() != null) {
            Address address = order.getAddress();
            OrderMapper.toAddress(addressDTO, address);

            if (address.getPostalCode() != null && !address.getPostalCode().isEmpty()) {
                ViaCepResponseDTO viaCepResponse = viaCepConsumerFeign.getAddressByPostalCode(address.getPostalCode());
                ViaCepResponseMapper.complementAddress(viaCepResponse, address);
            }
            addressRepository.save(address);
            order.setAddress(address);
        }
    }

}
