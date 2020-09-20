class Order < ApplicationRecord
  belongs_to :user
  belongs_to :store

  has_many :order_products, class_name: "OrderProduct", foreign_key: "order_id"
end
