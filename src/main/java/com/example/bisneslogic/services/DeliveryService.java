package com.example.bisneslogic.services;

import com.example.bisneslogic.common.CartNotFoundException;
import com.example.bisneslogic.common.DeliveryNotFoundException;
import com.example.bisneslogic.models.Delivery;
import com.example.bisneslogic.repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    public Delivery createDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }

    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) throws DeliveryNotFoundException {
        return deliveryRepository.findById(id).orElseThrow(()->new DeliveryNotFoundException("This delivery does not exist"));
    }

    public void deleteDelivery(Long id) {
        deliveryRepository.deleteById(id);
    }

    public Delivery updateDelivery(Long id, Delivery delivery) throws DeliveryNotFoundException {
        Delivery existingDelivery = deliveryRepository.findById(id).orElseThrow(()->new DeliveryNotFoundException("This delivery does not exist"));

            Delivery updatedDelivery = existingDelivery;
            updatedDelivery.setAddress(delivery.getAddress());
            updatedDelivery.setDeliveryDate(delivery.getDeliveryDate());
            return deliveryRepository.save(updatedDelivery);


    }
}