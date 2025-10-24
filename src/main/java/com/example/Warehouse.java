package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Warehouse {
    /*
    Håller listan över alla produkter
     */
    private final List<Product> products = new ArrayList<>();
    //HashSet tillåter inte dubletter - unika objekt
    private final Set<Product> changedProducts = Collections.synchronizedSet(new HashSet<>());

    private static final Map<String, Warehouse> INSTANCES = new HashMap<>();

    private Warehouse() {}

    //returns the same instance per unique name
    //Om det inte finns ett värde för nyckeln name,
    // skapa ett nytt med new Warehouse() och lägg till det. Annars returnera det befintliga
    public static Warehouse getInstance(String name){
        return INSTANCES.computeIfAbsent(name, n->new Warehouse());
    }
    public static Warehouse getInstance(){
        return getInstance("Warehouse");
    }

    //lägg till produkter i warehouse
    public void addProduct(Product product) {
        if (product == null){
            throw new IllegalArgumentException("Product cannot be null.");
        }
        if (getProductById(product.uuid()).isPresent()) {
            throw new IllegalArgumentException("Product with that id already exists, use updateProduct for updates.");
        }
        products.add(product);
    }

    //GetProducts
    public List<Product> getProducts() {
//        return products.stream().copyOf();
        return List.copyOf(products);
    }

    //GetproductBYID - stream find first
    public Optional<Product> getProductById(UUID id) {
        return products.stream()
                .filter(product -> product.uuid().equals(id))
                .findFirst();
    }

    //   - updateProductPrice(UUID, BigDecimal): when not found,
    //    throw NoSuchElementException("Product not found with id: <uuid>").
    public void updateProductPrice(UUID uuid, BigDecimal newPrice){
        Product product = products.stream()
                .filter(p->p.uuid().equals(uuid))
                .findFirst() //optional product
                .orElseThrow(()-> new NoSuchElementException("Product not found with id: " + uuid));

        product.setPrice(newPrice);
        changedProducts.add(product); //HashSet förhindrar dupletter, garanterar "uniqueness"
    }

    //use Collections.synchronizedList or validate before adding to ensure uniqueness
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

    //shippabeProducts, return list from stored products
    public List<Shippable> shippableProducts(){
        return products.stream()
                .filter(product -> product instanceof Shippable)
                .map(product -> (Shippable) product)
                .toList();
    }

    //    - remove(UUID): remove the matching product if present
    public void remove(UUID uuid){
        products.removeIf(product -> product.uuid().equals(uuid));
    }

    public boolean isEmpty(){
        return products.isEmpty();
    }

    public void clearProducts(){
        products.clear();
        changedProducts.clear();
    }

    public Map<Category, List<Product>> getProductsGroupedByCategories(){
        if(products.isEmpty()){
            return Collections.emptyMap();
        }
        return products.stream()
                .collect(Collectors.groupingBy(Product::category));

    }
}

/*
- Warehouse (singleton per name)
    - remove(UUID): remove the matching product if present.
 */