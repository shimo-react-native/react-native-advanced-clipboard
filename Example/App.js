/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';
import {
  Platform,
  StyleSheet,
  Text,
  View,
  AppState
} from 'react-native';
import Clipboard from 'react-native-advanced-clipboard';

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' +
  'Cmd+D or shake for dev menu',
  android: 'Double tap R on your keyboard to reload,\n' +
  'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {
  constructor(props) {
    super(props);
    this.state = {
      text: null,
      changeCount: 0,
      timestamp: 0
    }
  }

  componentWillMount() {
    this._updateClipboard();
    this._appState = AppState.currentState;
    AppState.addEventListener('change', this._handleAppStateChange);
  }

  componentWillUnmount() {
    AppState.removeEventListener('change', this._handleAppStateChange);
  }

  _appState = null;

  _handleAppStateChange = (nextAppState) => {
    if (nextAppState === 'active' && nextAppState !== this._appState) {
      this._updateClipboard();
    }
    this._appState = nextAppState;
  };

  _updateClipboard = async () => {
    const content = await Clipboard.getContent();
    console.log('App', content);
    this.setState({
      text: content.text,
      changeCount: content.changeCount,
      timestamp: content.timestamp
    });
  };

  render() {
    const {text, changeCount, timestamp} = this.state;
    return (
      <View style={styles.container}>
        <Text style={styles.welcome}>
          Welcome to React Native!
        </Text>
        <Text style={styles.instructions}>
          text: {text}
        </Text>
        <Text style={styles.instructions}>
          changeCount: {changeCount}
        </Text>
        <Text style={styles.instructions}>
          timestamp: {timestamp}
        </Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
