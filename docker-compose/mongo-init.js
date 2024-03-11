db = db.getSiblingDB('samples');

db.restaurants.insert({
    _id: NumberLong(1),
    name: 'restaurant1'
});