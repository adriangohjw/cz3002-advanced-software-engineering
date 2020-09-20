class Product < ApplicationRecord
  belongs_to :product_category

  has_many :discounts, class_name: "Discount", foreign_key: "product_id"
  has_many :order_products, class_name: "OrderProduct", foreign_key: "product_id"
  has_many :inventories, class_name: "Inventory", foreign_key: "product_id"
  has_many :cart_products, class_name: "CartProduct", foreign_key: "product_id"
end
