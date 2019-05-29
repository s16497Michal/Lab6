package domain;

import java.util.ArrayList;
import java.util.List;


public class ItemService {
    private static List<Item> db = new ArrayList<Item>();

    public List<Item> getAll() {
        return db;
    }

    public Item get(int id) {
        for(Item p : db)
            if (p.getId() == id)
                return p;
        return null;
    }

    public void add(Item item) {
    	item.setId(db.size() + 1);
        db.add(item);
    }

    public void delete(Item item) {
        db.remove(item);
    }

    public void update(Item item) {
        for(Item p : db) {
            if (p.getId() == item.getId()) {
                p.setCategory(item.getCategory());
                p.setName(item.getName());
                p.setPrice(item.getPrice());
                p.setComments(item.getComments());
            }
        }
    }
}
