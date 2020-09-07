class Store < ApplicationRecord
  has_many :movements, class_name: "Movement", foreign_key: "store_id"
end
