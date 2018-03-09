import { NativeModules, Platform } from 'react-native';

const AdvancedClipboard = Platform.OS === 'ios' ? NativeModules.RNAdvancedClipboard : NativeModules.AdvancedClipboard;

console.log('AdvancedClipboard', AdvancedClipboard);

function getContent() {
  return AdvancedClipboard.getContent()
}

function getString() {
  return AdvancedClipboard.getString()
}

function setString(text) {
  return AdvancedClipboard.setString(text)
}

export default {
  getContent,
  getString,
  setString
};
