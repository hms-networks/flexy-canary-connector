"use strict";(self.webpackChunkweb_docs=self.webpackChunkweb_docs||[]).push([[896],{3905:(e,n,t)=>{t.d(n,{Zo:()=>d,kt:()=>y});var a=t(7294);function l(e,n,t){return n in e?Object.defineProperty(e,n,{value:t,enumerable:!0,configurable:!0,writable:!0}):e[n]=t,e}function i(e,n){var t=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);n&&(a=a.filter((function(n){return Object.getOwnPropertyDescriptor(e,n).enumerable}))),t.push.apply(t,a)}return t}function c(e){for(var n=1;n<arguments.length;n++){var t=null!=arguments[n]?arguments[n]:{};n%2?i(Object(t),!0).forEach((function(n){l(e,n,t[n])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(t)):i(Object(t)).forEach((function(n){Object.defineProperty(e,n,Object.getOwnPropertyDescriptor(t,n))}))}return e}function r(e,n){if(null==e)return{};var t,a,l=function(e,n){if(null==e)return{};var t,a,l={},i=Object.keys(e);for(a=0;a<i.length;a++)t=i[a],n.indexOf(t)>=0||(l[t]=e[t]);return l}(e,n);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(e);for(a=0;a<i.length;a++)t=i[a],n.indexOf(t)>=0||Object.prototype.propertyIsEnumerable.call(e,t)&&(l[t]=e[t])}return l}var o=a.createContext({}),p=function(e){var n=a.useContext(o),t=n;return e&&(t="function"==typeof e?e(n):c(c({},n),e)),t},d=function(e){var n=p(e.components);return a.createElement(o.Provider,{value:n},e.children)},f="mdxType",u={inlineCode:"code",wrapper:function(e){var n=e.children;return a.createElement(a.Fragment,{},n)}},s=a.forwardRef((function(e,n){var t=e.components,l=e.mdxType,i=e.originalType,o=e.parentName,d=r(e,["components","mdxType","originalType","parentName"]),f=p(t),s=l,y=f["".concat(o,".").concat(s)]||f[s]||u[s]||i;return t?a.createElement(y,c(c({ref:n},d),{},{components:t})):a.createElement(y,c({ref:n},d))}));function y(e,n){var t=arguments,l=n&&n.mdxType;if("string"==typeof e||l){var i=t.length,c=new Array(i);c[0]=s;var r={};for(var o in n)hasOwnProperty.call(n,o)&&(r[o]=n[o]);r.originalType=e,r[f]="string"==typeof e?e:l,c[1]=r;for(var p=2;p<i;p++)c[p]=t[p];return a.createElement.apply(null,c)}return a.createElement.apply(null,t)}s.displayName="MDXCreateElement"},669:(e,n,t)=>{t.r(n),t.d(n,{assets:()=>o,contentTitle:()=>c,default:()=>u,frontMatter:()=>i,metadata:()=>r,toc:()=>p});var a=t(7462),l=(t(7294),t(3905));const i={id:"maven-lifecycles",title:"Maven Lifecycles",sidebar_label:"Maven Lifecycles"},c=void 0,r={unversionedId:"developer/maven-lifecycles",id:"developer/maven-lifecycles",title:"Maven Lifecycles",description:"Clean Lifecycle",source:"@site/docs/08-developer/03-MAVEN_LIFECYCLES.mdx",sourceDirName:"08-developer",slug:"/developer/maven-lifecycles",permalink:"/flexy-canary-connector/docs/developer/maven-lifecycles",draft:!1,editUrl:"https://github.com/hms-networks/flexy-canary-connector/docs/08-developer/03-MAVEN_LIFECYCLES.mdx",tags:[],version:"current",sidebarPosition:3,frontMatter:{id:"maven-lifecycles",title:"Maven Lifecycles",sidebar_label:"Maven Lifecycles"},sidebar:"defaultSidebar",previous:{title:"Libraries and Dependencies",permalink:"/flexy-canary-connector/docs/developer/libraries-and-dependencies"},next:{title:"Contribution Guidelines",permalink:"/flexy-canary-connector/docs/developer/contribution-guidelines"}},o={},p=[{value:"Clean Lifecycle",id:"clean-lifecycle",level:3},{value:"Test Lifecycle",id:"test-lifecycle",level:3},{value:"Package Lifecycle",id:"package-lifecycle",level:3},{value:"Install Lifecycle",id:"install-lifecycle",level:3},{value:"Deploy Lifecycle",id:"deploy-lifecycle",level:3}],d={toc:p},f="wrapper";function u(e){let{components:n,...t}=e;return(0,l.kt)(f,(0,a.Z)({},d,t,{components:n,mdxType:"MDXLayout"}),(0,l.kt)("h3",{id:"clean-lifecycle"},"Clean Lifecycle"),(0,l.kt)("p",null,"During the ",(0,l.kt)("inlineCode",{parentName:"p"},"clean")," lifecycle, previous build output and artifacts will be cleaned up, and the entire\nbuild output directory deleted. This lifecycle is not automatically invoked and must be manually\nrun."),(0,l.kt)("admonition",{title:"Clean Lifecycle",type:"info"},(0,l.kt)("p",{parentName:"admonition"},"The ",(0,l.kt)("inlineCode",{parentName:"p"},"clean")," lifecycle can be run with the ",(0,l.kt)("inlineCode",{parentName:"p"},"CLEAN (Remove Build Output)")," launch configuration in\nyour IDE, or with the following command:"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre",className:"language-shell"},"mvn clean -f pom.xml\n"))),(0,l.kt)("h3",{id:"test-lifecycle"},"Test Lifecycle"),(0,l.kt)("p",null,"During the ",(0,l.kt)("inlineCode",{parentName:"p"},"test")," lifecycle, any JUnit test classes in the ",(0,l.kt)("inlineCode",{parentName:"p"},"src/test/java")," class will be run by\nMaven. This lifecycle is automatically invoked by\nthe ",(0,l.kt)("a",{parentName:"p",href:"#package-lifecycle"},"package"),", ",(0,l.kt)("a",{parentName:"p",href:"#install-lifecycle"},"install"),", and ",(0,l.kt)("a",{parentName:"p",href:"#deploy-lifecycle"},"deploy"),"\nlifecycles."),(0,l.kt)("p",null,"In the event of a test failure, the lifecycle will fail. Detailed failure or successful test\ninformation can be found in the ",(0,l.kt)("inlineCode",{parentName:"p"},"target/surefire-reports")," folder."),(0,l.kt)("p",null,"More information about the Maven Surefure Plugin, which is used for the JUnit testing, can be found\nat ",(0,l.kt)("a",{parentName:"p",href:"https://maven.apache.org/surefire/maven-surefire-plugin/"},"https://maven.apache.org/surefire/maven-surefire-plugin/"),"."),(0,l.kt)("admonition",{title:"Test Lifecycle",type:"info"},(0,l.kt)("p",{parentName:"admonition"},"The ",(0,l.kt)("inlineCode",{parentName:"p"},"test")," lifecycle can be run with the ",(0,l.kt)("inlineCode",{parentName:"p"},"TEST (Run JUnit)")," launch configuration in your IDE, or\nwith the following command:"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre"},"mvn test -f pom.xml`\n"))),(0,l.kt)("h3",{id:"package-lifecycle"},"Package Lifecycle"),(0,l.kt)("p",null,"During the 'package' lifecycle, the application source code and resources will be compiled and\npackaged in to a JAR file with the name '{artifactId}-{version}-full.jar'. This lifecycle is\nautomatically invoked by the ",(0,l.kt)("a",{parentName:"p",href:"#install-lifecycle"},"install"),"\nand ",(0,l.kt)("a",{parentName:"p",href:"#deploy-lifecycle"},"deploy")," lifecycles."),(0,l.kt)("admonition",{title:"Package Lifecycle",type:"info"},(0,l.kt)("p",{parentName:"admonition"},"The Maven package lifecycle can be run with the ",(0,l.kt)("inlineCode",{parentName:"p"},"PACKAGE (Create JAR)")," launch configuration in your\nIDE, or with the\nfollowing command:"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre"},"mvn package -f pom.xml`\n"))),(0,l.kt)("h3",{id:"install-lifecycle"},"Install Lifecycle"),(0,l.kt)("p",null,"During the ",(0,l.kt)("inlineCode",{parentName:"p"},"install")," lifecycle, the packaged application will be uploaded to the Ewon device using\nthe 'ewon.address,' 'ewon.username,' and 'ewon.password' properties. This lifecycle is automatically\ninvoked by the ",(0,l.kt)("a",{parentName:"p",href:"#deploy-lifecycle"},"deploy")," lifecycle, and automatically\ninvokes ",(0,l.kt)("a",{parentName:"p",href:"#package-lifecycle"},"package lifecycle"),"."),(0,l.kt)("admonition",{title:"Install Lifecycle",type:"info"},(0,l.kt)("p",{parentName:"admonition"},"The Maven install lifecycle can be run with the ",(0,l.kt)("inlineCode",{parentName:"p"},"INSTALL (Upload to Device)")," launch configuration in\nyour IDE, or with the following command:"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre"},"mvn install -f pom.xml\n"))),(0,l.kt)("h3",{id:"deploy-lifecycle"},"Deploy Lifecycle"),(0,l.kt)("p",null,"During the ",(0,l.kt)("inlineCode",{parentName:"p"},"deploy")," lifecycle, the packaged and uploaded application will be run on the Ewon Flexy\ndevice. This lifecycle automatically invokes the ",(0,l.kt)("a",{parentName:"p",href:"#install-lifecycle"},"install"),"\nand ",(0,l.kt)("a",{parentName:"p",href:"#package-lifecycle"},"package")," lifecycles."),(0,l.kt)("p",null,"The ",(0,l.kt)("inlineCode",{parentName:"p"},"deploy")," lifecycle uses multiple Maven profiles, and can be invoked in different ways."),(0,l.kt)("admonition",{title:"Deploy Lifecycle (No Debugging)",type:"info"},(0,l.kt)("p",{parentName:"admonition"},"The Maven deploy lifecycle can be run without debugging enabled using\nthe ",(0,l.kt)("inlineCode",{parentName:"p"},"DEPLOY (Run on Device, No Debug)")," launch\nconfiguration in your IDE, or with the following command:"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre"},"mvn deploy -f pom.xml -P noDebug\n"))),(0,l.kt)("admonition",{title:"Deploy Lifecycle (With Debugging)",type:"info"},(0,l.kt)("p",{parentName:"admonition"},"The Maven deploy lifecycle can be run with debugging enabled using\nthe ",(0,l.kt)("inlineCode",{parentName:"p"},"DEPLOY (Run on Device, Debug)")," launch configuration in your IDE."),(0,l.kt)("p",{parentName:"admonition"},"The Maven deploy lifecycle with debugging enabled can also be run by command line, but a remote JVM\ndebugging connection must be manually created using the values from the 'ewon.address' and '\nproject.build.debug.port' properties. The following command will run the deploy lifecycle with\ndebugging enabled:"),(0,l.kt)("pre",{parentName:"admonition"},(0,l.kt)("code",{parentName:"pre"},"mvn deploy -f pom.xml -P debug\n"))))}u.isMDXComponent=!0}}]);