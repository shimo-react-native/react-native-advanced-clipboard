# react-native-advanced-clipboard

Advanced clipboard for react native. add parameter changeCount compare to official [Clipboard](https://facebook.github.io/react-native/docs/clipboard.html).

## Install

for npm >= 5.0

```sh
npm i react-native-advanced-clipboard
react-native link react-native-advanced-clipboard
```

## Method

### `getString`

same to [official](https://facebook.github.io/react-native/docs/clipboard.html#getstring) 

### `setString`

same to [official](https://facebook.github.io/react-native/docs/clipboard.html#setstring) 

### `getContent`

Get content of clipboard, include string and changeCount, this method returns a Promise, so you can use following code to get clipboard content

```javascript
import Clipboard from 'react-native-advanced-clipboard';

async _getContent() {
  const content = await Clipboard.getContent();
}
```

content will be like this:

```json
{
  'text': 'clipboard text',
  'changeCount': 100,
  'timestamp': 125868 // support Android 8.0 only 
}
```
 
