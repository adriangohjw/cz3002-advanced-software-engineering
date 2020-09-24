Rails.application.routes.draw do

  get 'users/authenticate' => 'users#authenticate'
  get 'users/' => 'users#show'
  get 'users/:id' => 'users#show'
  post 'users/' => 'users#create'
  put 'users/' => 'users#update'

  get 'orders/' => 'orders#show'
  get 'users/:user_id/orders' => 'orders#show_all_for_shopper'

  post 'movements/' => 'movements#create'

end
