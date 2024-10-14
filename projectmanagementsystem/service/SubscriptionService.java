package com.shobhit.projectmanagementsystem.service;

import com.shobhit.projectmanagementsystem.model.PlanType;
import com.shobhit.projectmanagementsystem.model.Subscription;
import com.shobhit.projectmanagementsystem.model.User;

public interface SubscriptionService {

    Subscription createSubscription(User user) ;
    Subscription getUsersSubscription(long userId)throws Exception ;

    Subscription upgradeSubscription(long userId, PlanType planType) ;

    boolean isValid(Subscription subscription) ;

}
