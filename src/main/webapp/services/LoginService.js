var todo = angular.module('todo');
todo.factory('loginService', function($http) {
	console.log('in service');
	var login = {};

	login.loginuser = function(user) {
		return $http({
			method : "POST",
			url : 'login',
			data : user
		});
	}	
	// return login response from backend

	login.fbLogin = function(user) {
		return $http({
			url : 'fbconnect',
			method : 'GET',
			data : 'user'

		});
	}

	login.gLogin = function(user) {
		return $http({
			url : 'gconnect',
			method : 'GET',
			data : 'user',
			headers : {
				'Access-Control-Allow-Origin' : 'http://localhost:8080',
				'Access-Control-Allow-Credentials' : true
			}
		});
	}
	
	
	
	
	
	return login;
});




todo.service("uploadService", function($http, $q) {

    return ({
      upload: upload
    });

    function upload(file) {
      var upl = $http({
        method: 'POST',
        url: 'http://jsonplaceholder.typicode.com/posts', // /api/upload
        headers: {
          'Content-Type': 'multipart/form-data'
        },
        data: {
          upload: file
        },
        transformRequest: function(data, headersGetter) {
          var formData = new FormData();
          angular.forEach(data, function(value, key) {
            formData.append(key, value);
          });

          var headers = headersGetter();
          delete headers['Content-Type'];

          return formData;
        }
      });
      return upl.then(handleSuccess, handleError);

    } // End upload function

    // ---
    // PRIVATE METHODS.
    // ---
  
    function handleError(response, data) {
      if (!angular.isObject(response.data) ||!response.data.message) {
        return ($q.reject("An unknown error occurred."));
      }

      return ($q.reject(response.data.message));
    }

    function handleSuccess(response) {
      return (response);
    }

  })
 todo.directive("fileinput", [function() {
    return {
      scope: {
        fileinput: "=",
        filepreview: "="
      },
      link: function(scope, element, attributes) {
        element.bind("change", function(changeEvent) {
          scope.fileinput = changeEvent.target.files[0];
          var reader = new FileReader();
          reader.onload = function(loadEvent) {
            scope.$apply(function() {
              scope.filepreview = loadEvent.target.result;
            });
          }
          reader.readAsDataURL(scope.fileinput);
        });
      }
    }
  }]);
