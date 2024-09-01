package com.example.identityservice.repository;

import com.example.identityservice.model.OtpPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OtpRepository extends JpaRepository<OtpPhoneNumber,Long> {
    public OtpPhoneNumber findByPhoneNumber(String phoneNumber);
    @Transactional
    public void deleteByPhoneNumber(String phoneNumber);
    @Query("UPDATE OtpPhoneNumber o SET o.used = true WHERE o.phoneNumber = ?1")
    @Modifying
    @Transactional
    public int markOtpAsUsedByPhoneNumber(String phoneNumber);


}
