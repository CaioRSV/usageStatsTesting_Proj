/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */


import React from 'react';

import {useState} from 'react';

import { NativeModules } from 'react-native';

const {TestModule} = NativeModules;

import { Linking } from 'react-native';

import type {PropsWithChildren} from 'react';
import {
  Button,
  SafeAreaView,
  ScrollView,
  StatusBar,
  StyleSheet,
  Text,
  useColorScheme,
  View,
} from 'react-native';

import {
  Colors,
  DebugInstructions,
  Header,
  LearnMoreLinks,
  ReloadInstructions,
} from 'react-native/Libraries/NewAppScreen';

type SectionProps = PropsWithChildren<{
  title: string;
}>;

function App(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';

  const [testVar, setTestVar] = useState("");

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const onPress = () => {
    TestModule.testFunction((res:string) => setTestVar(res))
  };

  const onPress2 = () => {
    TestModule.testFunctionSample((res:string) => setTestVar(res))
  };

  //Linking.openSettings();

  return (
    <View>
      <Text>{testVar}</Text>
      <Button title="Chamar função de UsageStats" onPress={ onPress }></Button>
      <Button title="Chamar função de Sample Text" onPress={ onPress2 }></Button>
    </View>
  );
}

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
  },
  highlight: {
    fontWeight: '700',
  },
});

export default App;
