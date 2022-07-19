import { NativeModules, Platform } from "react-native";

const AdvancedClipboard =
  Platform.OS === "ios"
    ? NativeModules.RNAdvancedClipboard
    : NativeModules.AdvancedClipboard;

function init() {
  if (Platform.OS === "android") {
    return AdvancedClipboard.init();
  } else {
    return Promise.resolve();
  }
}

function getContent() {
  return AdvancedClipboard.getContent();
}

function getString() {
  return AdvancedClipboard.getString();
}

function setString(text) {
  return AdvancedClipboard.setString(text);
}

export default {
  init,
  getContent,
  getString,
  setString,
};
