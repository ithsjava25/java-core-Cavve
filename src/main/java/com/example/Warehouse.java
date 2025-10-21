package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    /*
    Håller listan över alla produkter
     */
    private final List<Product> products = new ArrayList<>();
    private final List<Product> changedProducts = new ArrayList<>();
    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();

    private Warehouse() {}

    //returns the same instance per unique name
    //Om det inte finns ett värde för nyckeln name,
    // skapa ett nytt med new Warehouse() och lägg till det. Annars returnera det befintliga
    public static Warehouse getInstance(String name){
        return INSTANCES.computeIfAbsent(name, k->new Warehouse());
    }

    public static Warehouse getInstance(){
        return getInstance("Warehouse");
    }

    public void clearProducts(){
        products.clear();
    }

    //lägg till produkter i warehouse
    public void addProduct(Product product) {
        if (product == null){
           throw new IllegalArgumentException("Product cannot be null.");
        }
        if (!getProductById(product.uuid()).equals(Optional.empty())){
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        products.add(product);
    }
    //GetProducts

    public List<Product> getProducts() {
        //return Collections.unmodifiableList(products);
        return products.stream().toList();
    }

    //GetproductBYID - stream find first
    public Optional<Product> getProductById(UUID id) {
        //Traditionel for loop
//        for (Product product : products){
//            if (product.getId().equals(id)){
//                return Optional.of(product);
//            }
//        }
//    } return Optional.empty();
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public List<Product> getChangedProducts() {
        return Collections.unmodifiableList(changedProducts);
    }

    //   - updateProductPrice(UUID, BigDecimal): when not found,
    //    throw NoSuchElementException("Product not found with id: <uuid>").
    public void updateProductPrice(UUID uuid, BigDecimal newPrice){
        Product product = products.stream()
                .filter(p->p.getId().equals(uuid))
                .findFirst() //optional product
                .orElseThrow(()-> new NoSuchElementException("Product not found with id: " + uuid));

        product.setPrice(newPrice);

        if (!changedProducts.contains(product)){
            changedProducts.add(product);
        }
    }

    //    - remove(UUID): remove the matching product if present with iterator.
    public void remove(UUID uuid){
        for (Iterator <Product> iterator = products.iterator(); iterator.hasNext();){
            Product product = iterator.next();
            if (product.getId().equals(uuid)){
                iterator.remove();
                break;
            }
        }
    }


    //shippabeProducts, return list from stored products
    public List<Shippable> shippableProducts(){
        List<Shippable> shippables = new ArrayList<>();
        for (Product product : products){
            if (product instanceof Shippable shippable){
                shippables.add(shippable);
            }
        }
        return shippables;
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories(){
        if(products.isEmpty()){
            return Collections.emptyMap();
        }
        return products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        //map
        //if villkor
        //should return an empty map when grouping by category if empty"
    }
    public List<Perishable> expiredProducts(){
        //- expiredProducts(): return List<Perishable> that are expired.
        LocalDate today = LocalDate.now();

        return products.stream()
                //filtrerar perishable produkter
                .filter(p -> p instanceof Perishable)
                //casha till perishable
                .map(p->(Perishable) p)
                //filtrerar om expirationDate har passerat
                .filter(p->p.expirationDate().isBefore(today))
                //samla i en ny lista
                .toList();
    }

    public boolean isEmpty(){return products.isEmpty();}
}

/*
- Warehouse (singleton per name)
    - remove(UUID): remove the matching product if present.
 */