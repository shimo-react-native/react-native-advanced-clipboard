Pod::Spec.new do |s|
  s.name             = 'react-native-advanced-clipboard'
  s.version          = '0.1.2'
  s.summary          = 'react-native-advanced-clipboard-shimohq'

  s.description      = <<-DESC
TODO: Add long description of the pod here.
                       DESC

  s.homepage         = 'https://github.com/shimohq/react-native-advanced-clipboard'
  s.license          = { :type => 'MIT', :file => 'LICENSE' }
  s.author           = { 'lisong' => 'lisong@shimo.im' }
  s.source           = { :git => 'https://github.com/shimohq/react-native-advanced-clipboard.git', :tag => "v#{s.version}" }

  s.ios.deployment_target = '8.0'
  
  s.source_files = 'ios/**/*.{h,m,mm}'
  
  s.dependency 'React'

end
