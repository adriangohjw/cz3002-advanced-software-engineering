Rails.application.routes.draw do

  get 'users/authenticate' => 'users#authenticate'
  get 'users/' => 'users#show'
  get 'users/:id' => 'users#show'
  post 'users/' => 'users#create'
  put 'users/' => 'users#update'

  get 'products/' => 'products#index'

  get 'orders/' => 'orders#show'
  get 'users/:user_id/orders' => 'orders#show_all_for_shopper'

  get 'users/:id/cart' => 'cart_products#show_all_for_shopper'
  delete 'users/:id/cart' => 'cart_products#clear_all'
  post 'cart_products' => 'cart_products#add_to_cart'
  delete 'cart_products/:id' => 'cart_products#remove_from_cart'
  put 'cart_products/:id/increase' => 'cart_products#increase_quantity'
  put 'cart_products/:id/decrease' => 'cart_products#decrease_quantity'

  post 'movements/' => 'movements#create'

end
