class StripeModule

  # Delete all Stripe customers
  def self.delete_all_customers
    StripeModule.get_all_customer_ids_list.each do |customer_id|
      begin
        Stripe::Customer.delete(customer_id)
      end
    end
  end

  def self.customer_exist(email:)
    return Stripe::Customer.list(email: email)["data"].count > 0
  end

  private
  
  def self.get_all_customer_ids_list
    stripe_customers = Stripe::Customer.list
    
    stripe_customer_ids = Array.new
    stripe_customers["data"].each do |stripe_customer|
      stripe_customer_ids.append(stripe_customer["id"])
    end

    return stripe_customer_ids
  end

end