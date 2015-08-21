Handlebars.registerHelper('eq', function(v1, v2, options) {
  if(v1 == v2) {
    return options.fn(this);
  }
  return options.inverse(this);
});

Handlebars.registerHelper('isMe', function(currUserId, options) {
  if(currUserId == userId || currUserId == null) {
    return options.fn(this);
  }
  return options.inverse(this);
});