require_relative '../exceptions/cart_products_exceptions'

module CartProductsExceptionHandler
  extend ActiveSupport::Concern

  included do
    rescue_from CartProductsException::RecordExistError do |e|
      json_response({ message: e.message }, e.status)
    end
  end
end