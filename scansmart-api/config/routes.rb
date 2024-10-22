Rails.application.routes.draw do

  get 'users/authenticate' => 'users#authenticate'
  get 'users/:id' => 'users#show'
  post 'users/' => 'users#create'
  put 'users/' => 'users#update'

  get 'users/:id/cards' => 'cards#index' 
  post 'users/:id/cards' => 'cards#post' 
  delete 'users/:id/cards' => 'cards#destroy'

  get 'products/' => 'products#index'
  get 'products/:id' => 'products#show'

  get 'orders/' => 'orders#show'
  get 'users/:user_id/orders' => 'orders#show_all_for_shopper'
  post 'users/:user_id/orders' => 'orders#post'

  get 'charges' => 'charges#show'
  post 'charges' => 'charges#post'

  get 'users/:id/cart' => 'cart_products#show_all_for_shopper'
  delete 'users/:id/cart' => 'cart_products#clear_all'
  post 'cart_products' => 'cart_products#add_to_cart'
  delete 'cart_products/:id' => 'cart_products#remove_from_cart'
  put 'cart_products/:id/increase' => 'cart_products#increase_quantity'
  put 'cart_products/:id/decrease' => 'cart_products#decrease_quantity'

  post 'movements/' => 'movements#create'

end
