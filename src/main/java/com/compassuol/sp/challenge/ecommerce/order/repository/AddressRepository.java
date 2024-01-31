package com.compassuol.sp.challenge.ecommerce.order.repository;

import com.compassuol.sp.challenge.ecommerce.order.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
