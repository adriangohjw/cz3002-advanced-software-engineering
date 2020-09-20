class Store < ApplicationRecord
  has_many :movements, class_name: "Movement", foreign_key: "store_id"
  has_many :orders, class_name: "Order", foreign_key: "store_id"
  has_many :inventories, class_name: "Inventory", foreign_key: "store_id"
end
