package dev.pack.Address.Repository;

import dev.pack.Address.Model.AddressUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressUser, UUID> {
}
