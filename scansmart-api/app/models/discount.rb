class Discount < ApplicationRecord
  belongs_to :product

  def self.compute_total_discounted_price(product:, quantity:)
    return product.price * quantity if product.discounts.blank?

    most_recent_discount = product.latest_discount
    case most_recent_discount.type
    when "DiscountSingle"
      return most_recent_discount.price * quantity
    when "DiscountBulk"
      if quantity >= most_recent_discount.bulk_quantity
        return \
          (quantity.to_i % most_recent_discount.bulk_quantity.to_i) * product.price \
          + (quantity.to_i / most_recent_discount.bulk_quantity.to_i) * most_recent_discount.bulk_price
      else
        return product.price * quantity
      end
    end
  end

end
