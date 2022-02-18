package com.example.skinanalysis.others

val jsCodeStr = """
var revieveConfig = {
    partner_id: 'RNhaZp3SNs',
    locale: 'nl',
    env: 'test', // for test env. Change to prod for production deployment
    onClose: function() {
      // User closed modal. Here you could forward them to checkout if they had
      // added products (onAddToCart was called before).
      console.log('User clicked close');
      Android.onClose();
    },
    onPageChange: function(activePageIndex) {
      // User navigates to page with index specified by activePageIndex
      console.log("onPageChangeTo ", activePageIndex);
      Android.onPageChange(activePageIndex);
    },
     onImageAnalysisFinished: function onImageAnalysisFinished(data) {
        console.log('parent: onImageAnalysisFinished', data);
         Android.onImageAnalysisFinished(JSON.stringify(data));
      },
    onFetchProducts: function(selections) {
      // To be filled in by Philips IPL.
      console.log("User fetch products.", selections);
      Android.onFetchProducts();
    }
    };
    (function() {
    var rv = document.createElement('script');
    rv.src = 'https://d38knilzwtuys1.cloudfront.net/revieve-plugin-v4/revieve-plugin-loader.js'; 
    rv.charset = 'utf-8';
    rv.type = 'text/javascript';
    rv.async = 'true';
        rv.onload = rv.onreadystatechange = function() {
      var rs = this.readyState;
      if (rs && rs != 'complete' && rs != 'loaded') return;
      Revieve.Init(revieveConfig, function() {
        // Comment out the below line if you want to open the modal
        // manually when user clicks a certain button or navigates
        // to certain page.
        //Revieve.API.show();
      });
    };
    var s = document.getElementsByTagName('script')[0];
    s.parentNode.insertBefore(rv, s);
    })();
""".trimIndent()