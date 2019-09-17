require "json"
package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name             = 'react-native-advanced-clipboard'
  s.version          = package['version']
  s.summary          = 'react-native-advanced-clipboard-shimohq'
  s.homepage         = 'https://github.com/shimohq/react-native-advanced-clipboard'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'lisong' => 'lisong@shimo.im' }
  s.source           = { :git => 'https://github.com/shimohq/react-native-advanced-clipboard.git', :tag => "v#{s.version}" }

  s.ios.deployment_target = '8.0'
  
  s.source_files = 'ios/**/*.{h,m,mm}'
  
  s.dependency 'React'

end
