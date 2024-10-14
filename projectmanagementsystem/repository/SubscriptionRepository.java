package com.shobhit.projectmanagementsystem.repository;

import com.shobhit.projectmanagementsystem.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    Subscription findByUserId(long userId) ;
}
