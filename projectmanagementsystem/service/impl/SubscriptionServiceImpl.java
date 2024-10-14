package com.shobhit.projectmanagementsystem.service.impl;

import com.shobhit.projectmanagementsystem.model.PlanType;
import com.shobhit.projectmanagementsystem.model.Subscription;
import com.shobhit.projectmanagementsystem.model.User;
import com.shobhit.projectmanagementsystem.repository.SubscriptionRepository;
import com.shobhit.projectmanagementsystem.service.SubscriptionService;
import com.shobhit.projectmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {

    @Autowired
    private UserService userService ;

    @Autowired
    private SubscriptionRepository subscriptionRepository ;


    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription=new Subscription() ;
        subscription.setStart(LocalDateTime.now());
        subscription.setEnd(LocalDateTime.now().plusMonths(12));
        subscription.setValid(true);
        subscription.setPlanType(PlanType.FREE);


        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUsersSubscription(long userId) throws Exception {
        Subscription subscription=subscriptionRepository.findByUserId(userId) ;
        if(!isValid(subscription)){
            subscription.setPlanType(PlanType.FREE);
            subscription.setStart(LocalDateTime.now());
            subscription.setEnd(LocalDateTime.now().plusMonths(12));
            subscription.setValid(true);
        }
        return subscriptionRepository.save(subscription) ;
    }

    @Override
    public Subscription upgradeSubscription(long userId, PlanType planType) {
        Subscription subscription=subscriptionRepository.findByUserId(userId) ;
        subscription.setPlanType(planType);
        if(planType.equals(PlanType.MONTHLY)){
            subscription.setEnd(LocalDateTime.now().plusMonths(1));
        }else{
            subscription.setEnd(LocalDateTime.now().plusMonths(12));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlanType().equals(PlanType.FREE))return true ;
        else{
            LocalDate end= LocalDate.from(subscription.getEnd());
            LocalDate curr=LocalDate.now() ;
            return end.isAfter(curr)||end.isEqual(curr) ;
        }

    }
}
