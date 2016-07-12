'use strict';

angular.module('desappGroupdFrontendApp')
.controller('product_ctrl', [ '$http', '$scope', '$window', '$cookies',
  '$locale', '$filter', 'baseUrl', function($http, $scope, $window, $cookies, $locale, $filter, baseUrl) {

    $scope.user = $cookies.get('user');
    $scope.listOfProducts = [];
    $scope.showProductSuccess = false;
    $scope.showProductError = false;
    $scope.productToUpdate = null;

    $scope.products = function(page){
      $http.get( baseUrl + '/users/'+ $scope.user + '/products/' + page).success(function(result) {
        $scope.listOfProducts = result;
      })
    };

    $scope.createProduct = function(newProduct){
      $http.post( baseUrl + '/users/'+ $scope.user + '/newproduct', {
        description : newProduct.description,
        requiredPoints : newProduct.requiredPoints,
        stock : newProduct.stock
      }).success(function() {
        $scope.showProductSuccess = true;
        $scope.products(1);
      }).error(function() {
        $scope.showProductError = true;
      })
    };

    $scope.removeProduct = function(product){
          $http.post( baseUrl + '/users/'+ $scope.user + '/removeproduct', {
            id : product.id,
            description : product.description,
            requiredPoints : product.requiredPoints,
            stock : product.stock
          }).success(function() {
            $scope.showProductSuccess = true;
            $scope.products(1);
          }).error(function() {
            $scope.showProductError = true;
          })
    };

    $scope.updateProduct = function(product){
      $http.post( baseUrl + '/products/update/'+ product.id , {
        requestedBy : $scope.user,
        requestObject: {id: product.id,
                        description : product.description,
                        requiredPoints : product.requiredPoints,
                        stock : product.stock}
      }).success(function() {
        $scope.showProductSuccess = true;
        $scope.products(1);
      }).error(function() {
        $scope.showProductError = true;
      })
    }

    $scope.getProducts = function(){
      return $scope.listOfProducts;
    };    

    $scope.initProducts = function(){
      $scope.products(1);
    };
    
    $scope.setProductToUpdate = function(product){
      $scope.productToUpdate = product;
    }

    $scope.initProducts();

  } ]);
