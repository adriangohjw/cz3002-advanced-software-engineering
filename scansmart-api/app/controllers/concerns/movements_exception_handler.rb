require_relative '../exceptions/movements_exceptions'

module MovementsExceptionHandler
  extend ActiveSupport::Concern

  included do
    rescue_from MovementsException::IncorrectMovementTypeError do |e|
      json_response({ message: e.message }, e.status)
    end
  end
end