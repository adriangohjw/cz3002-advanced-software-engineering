require_relative '../exceptions/cards_exceptions'

module CardsExceptionHandler
  extend ActiveSupport::Concern

  included do
    rescue_from CardsException::StripeCustomerCardIdDoesNotExistError do |e|
      json_response({ message: e.message }, e.status)
    end
        
    rescue_from CardsException::StripeCustomerCardTokenIdDoesNotExistError do |e|
      json_response({ message: e.message }, e.status)
    end

    rescue_from CardsException::StripeCardTokenExpiredError do |e|
      json_response({ message: e.message }, e.status)
    end
  end
end