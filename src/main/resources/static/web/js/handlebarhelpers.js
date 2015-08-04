Handlebars.registerHelper('eq', function(v1, v2, options) {
console.log('eq');
	console.log(v1);
	console.log(v2);
	console.log(v1 == v2);
  if(v1 == v2) {
    return options.fn(this);
  }
  return options.inverse(this);
});