/**
 * @providesModule RNAdvancedClipboard
 * @flow
 */
'use strict';

var NativeRNAdvancedClipboard = require('NativeModules').RNAdvancedClipboard;

/**
 * High-level docs for the RNAdvancedClipboard iOS API can be written here.
 */

var RNAdvancedClipboard = {
  test: function() {
    NativeRNAdvancedClipboard.test();
  }
};

module.exports = RNAdvancedClipboard;
