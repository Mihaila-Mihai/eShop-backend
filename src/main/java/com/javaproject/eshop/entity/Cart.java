package com.javaproject.eshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    private double totalPrice;

    @ManyToOne
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;


    @ManyToMany
    @JoinTable(name = "cart_to_product", joinColumns = @JoinColumn(name = "cart_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @JsonIgnore
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Builder
    public Cart(double totalPrice, Voucher voucher, List<Product> products, Customer customer) {
        this.totalPrice = totalPrice;
        this.voucher = voucher;
        this.products = products;
        this.customer = customer;
    }

    public void setProducts(List<Product> products) {
        this.products = new ArrayList<>(products);
    }

    public void addToCart(Product product) {
        List<Product> products = new ArrayList<>(this.products);
        products.add(product);
        this.setProducts(products);

        this.calculateTotalPrice();
    }

    public void calculateTotalPrice() {
        double totalPriceValue = 0;

        for(Product product: products) {
            totalPriceValue += product.getPrice();
        }
        if (voucher != null) {
            totalPriceValue -= voucher.getValue();
        }

        this.setTotalPrice(totalPriceValue >= 0 ? totalPriceValue : 0);
    }

    public void addVoucher(Voucher voucher) {
        this.setVoucher(voucher);

        this.calculateTotalPrice();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalPrice=" + totalPrice +
                '}';
    }
}
