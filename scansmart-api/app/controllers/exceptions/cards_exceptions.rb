module CardsException
  
  class StripeCustomerCardIdDoesNotExistError < StandardError
    def message
      "Customer card ID does not exist in Stripe"
    end

    def status
      :not_found
    end
  end

  class StripeCustomerCardTokenIdDoesNotExistError < StandardError
    def message
      "Card Token ID does not exist in Stripe"
    end

    def status
      :not_found
    end
  end

  class StripeCardTokenExpiredError < StandardError
    def message
      "You cannot use a Stripe token more than once"
    end

    def status
      :bad_request
    end
  end

end