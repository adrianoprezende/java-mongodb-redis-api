// This file is used to create the database and the collection with the required indexes

db = db.getSiblingDB('catalog')

db.runCommand(
   {
     create: "categories",
     expireAfterSeconds: 1800,
     clusteredIndex: {
          key: { _id: 1 },
          unique: true,
          name: "categoryId_idx"
     },
   }
)

db.categories.createIndex(
  {
      "ownerId": 1
  },
  {
      name: "ownerId_idx"
  }
)

db.runCommand(
   {
     create: "products",
     expireAfterSeconds: 1800,
     clusteredIndex: {
          key: { _id: 1 },
          unique: true,
          name: "productId_idx"
     },
   }
)

db.products.createIndex(
  {
      "ownerId": 1
  },
  {
      name: "ownerId_idx"
  }
)

db.products.createIndex(
  {
      "category": 1
  },
  {
      unique: true,
      name: "category_idx"
  }
)
