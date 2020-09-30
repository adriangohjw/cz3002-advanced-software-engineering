class OrderProduct < ApplicationRecord
  belongs_to :order
  belongs_to :product

  scope :last_30_days, -> { where("created_at > ?", Time.zone.now - 30.days) }
end
