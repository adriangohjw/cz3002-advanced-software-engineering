module CartProductsException
  
  class RecordExistError < StandardError
    def message
      "Record with the same shopper_id and product_id already exist"
    end

    def status
      :precondition_failed
    end
  end

end