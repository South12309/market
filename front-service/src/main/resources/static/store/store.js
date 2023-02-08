angular.module('market').controller('storeController', function ($scope, $http, $localStorage) {
    const contextPathCore = 'http://localhost:5555/core/api/v1';
    const contextPathCarts = 'http://localhost:5555/cart/api/v1';
    let offsetPage = 0;
    let pageCount =10;
    $scope.loadProducts = function () {
        $http.get(contextPathCore + '/products')
            .then(function (response) {
                $scope.products = response.data;
            });
    };


    $scope.addToCart = function (id) {
        $http.get(contextPathCarts + '/cart/add/' + id)
            .then(function (response) {

            });
    }

    $scope.change_page = function (pageVar, min, max, titlePart) {
        offsetPage = offsetPage + pageVar;
        if (offsetPage < 0) offsetPage = 0;
        if (offsetPage > pageCount) offsetPage = pageCount;
        $http({
            url: contextPathCore + '/products',
            method: 'GET',
            params: {
                page: offsetPage,
                min: $scope.min,
                max: $scope.max,
                titlePart: $scope.titlePart
            }
        }).then(function (response) {
            $scope.products = response.data.content;
            pageCount = response.data.totalPages;
        });
    }


    // $scope.loadProducts();
    $scope.change_page(offsetPage);
});