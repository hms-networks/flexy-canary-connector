"use strict";(self.webpackChunkweb_docs=self.webpackChunkweb_docs||[]).push([[708],{3905:(e,t,n)=>{n.d(t,{Zo:()=>d,kt:()=>h});var a=n(7294);function i(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function r(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);t&&(a=a.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,a)}return n}function o(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?r(Object(n),!0).forEach((function(t){i(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):r(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function l(e,t){if(null==e)return{};var n,a,i=function(e,t){if(null==e)return{};var n,a,i={},r=Object.keys(e);for(a=0;a<r.length;a++)n=r[a],t.indexOf(n)>=0||(i[n]=e[n]);return i}(e,t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);for(a=0;a<r.length;a++)n=r[a],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(i[n]=e[n])}return i}var s=a.createContext({}),u=function(e){var t=a.useContext(s),n=t;return e&&(n="function"==typeof e?e(t):o(o({},t),e)),n},d=function(e){var t=u(e.components);return a.createElement(s.Provider,{value:t},e.children)},c="mdxType",m={inlineCode:"code",wrapper:function(e){var t=e.children;return a.createElement(a.Fragment,{},t)}},p=a.forwardRef((function(e,t){var n=e.components,i=e.mdxType,r=e.originalType,s=e.parentName,d=l(e,["components","mdxType","originalType","parentName"]),c=u(n),p=i,h=c["".concat(s,".").concat(p)]||c[p]||m[p]||r;return n?a.createElement(h,o(o({ref:t},d),{},{components:n})):a.createElement(h,o({ref:t},d))}));function h(e,t){var n=arguments,i=t&&t.mdxType;if("string"==typeof e||i){var r=n.length,o=new Array(r);o[0]=p;var l={};for(var s in t)hasOwnProperty.call(t,s)&&(l[s]=t[s]);l.originalType=e,l[c]="string"==typeof e?e:i,o[1]=l;for(var u=2;u<r;u++)o[u]=n[u];return a.createElement.apply(null,o)}return a.createElement.apply(null,n)}p.displayName="MDXCreateElement"},5073:(e,t,n)=>{n.r(t),n.d(t,{assets:()=>c,contentTitle:()=>u,default:()=>g,frontMatter:()=>s,metadata:()=>d,toc:()=>m});var a=n(7462),i=(n(7294),n(3905));const r={toc:[]},o="wrapper";function l(e){let{components:t,...n}=e;return(0,i.kt)(o,(0,a.Z)({},r,n,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("table",null,(0,i.kt)("thead",{parentName:"table"},(0,i.kt)("tr",{parentName:"thead"},(0,i.kt)("th",{parentName:"tr",align:"center"},"Logging Level"),(0,i.kt)("th",{parentName:"tr",align:null},"Description"))),(0,i.kt)("tbody",{parentName:"table"},(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"0"),(0,i.kt)("td",{parentName:"tr",align:null},"Logs disabled")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"1"),(0,i.kt)("td",{parentName:"tr",align:null},"Include only critical logs")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"2"),(0,i.kt)("td",{parentName:"tr",align:null},"Includes serious logs as well as logs from level 1")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"3"),(0,i.kt)("td",{parentName:"tr",align:null},"Includes warning logs as well as logs from level 1-2")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"4"),(0,i.kt)("td",{parentName:"tr",align:null},"Includes info logs as well as logs from level 1-3")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"5"),(0,i.kt)("td",{parentName:"tr",align:null},"Includes debug logs as well as logs from level 1-4")),(0,i.kt)("tr",{parentName:"tbody"},(0,i.kt)("td",{parentName:"tr",align:"center"},"6"),(0,i.kt)("td",{parentName:"tr",align:null},"Includes trace logs as well as logs from level 1-5")))))}l.isMDXComponent=!0;const s={id:"configuration",title:"Configuration",sidebar_label:"Configuration"},u=void 0,d={unversionedId:"setup/configuration",id:"setup/configuration",title:"Configuration",description:'The connector configuration is a JSON file stored in the Ewon /usr/ directory with the filename "CanaryConnectorConfig.json".',source:"@site/docs/04-setup/02-CONFIGURATION.mdx",sourceDirName:"04-setup",slug:"/setup/configuration",permalink:"/flexy-canary-connector/docs/setup/configuration",draft:!1,editUrl:"https://github.com/hms-networks/flexy-canary-connector/docs/04-setup/02-CONFIGURATION.mdx",tags:[],version:"current",sidebarPosition:2,frontMatter:{id:"configuration",title:"Configuration",sidebar_label:"Configuration"},sidebar:"defaultSidebar",previous:{title:"Download",permalink:"/flexy-canary-connector/docs/setup/download"},next:{title:"Features",permalink:"/flexy-canary-connector/docs/usage/features"}},c={},m=[{value:"General",id:"general",level:3},{value:"LogLevel",id:"loglevel",level:4},{value:"QueueEnableDiagnosticTags",id:"queueenablediagnostictags",level:4},{value:"QueueEnableStringHistory",id:"queueenablestringhistory",level:4},{value:"Utf8StringSupport",id:"utf8stringsupport",level:4},{value:"QueueDataPollSizeMins",id:"queuedatapollsizemins",level:4},{value:"QueueDataPollIntervalMillis",id:"queuedatapollintervalmillis",level:4},{value:"QueueDataPollWarnBehindTimeMins",id:"queuedatapollwarnbehindtimemins",level:4},{value:"QueueDataPollMaxBehindTimeMins",id:"queuedatapollmaxbehindtimemins",level:4},{value:"Canary",id:"canary",level:3},{value:"API",id:"api",level:4},{value:"Url",id:"url",level:5},{value:"SenderApiVersionNumber",id:"senderapiversionnumber",level:5},{value:"ReceiverApiVersionNumber",id:"receiverapiversionnumber",level:5},{value:"HistorianServerName",id:"historianservername",level:5},{value:"ApiClientId",id:"apiclientid",level:5},{value:"ApiClientTimeoutSeconds",id:"apiclienttimeoutseconds",level:5},{value:"FileSizeMegabytes",id:"filesizemegabytes",level:5},{value:"AutoCreateDatasets",id:"autocreatedatasets",level:5},{value:"QueueDataPostRateMillis",id:"queuedatapostratemillis",level:5},{value:"Auth",id:"auth",level:4},{value:"UserName",id:"username",level:5},{value:"UserPassword",id:"userpassword",level:5}],p={toc:m},h="wrapper";function g(e){let{components:t,...n}=e;return(0,i.kt)(h,(0,a.Z)({},p,n,{components:t,mdxType:"MDXLayout"}),(0,i.kt)("p",null,'The connector configuration is a JSON file stored in the Ewon /usr/ directory with the filename "CanaryConnectorConfig.json".\nTo make any configuration changes, modify CanaryConnectorConfig.json and ensure changes are saved\nto the /usr/ directory of the Ewon Flexy.'),(0,i.kt)("p",null,"If no configuration file is detected, a default configuration file will be generated to match what is shown below:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre"},'{\n  "General": {\n    "LogLevel": 3,\n    "QueueEnableDiagnosticTags": false,\n    "QueueEnableStringHistory": false,\n    "Utf8StringSupport": false,\n    "QueueDataPollSizeMins": 5,\n    "QueueDataPollIntervalMillis": 0,\n    "QueueDataPollWarnBehindTimeMins": 4,\n    "QueueDataPollMaxBehindTimeMins": 10\n  },\n  "Canary":{\n    "Api": {\n      "Url":"https://<USER-DOMAIN>:PORT_NUM/",\n      "SenderApiVersionNumber": "v1",\n      "ReceiverApiVersionNumber": "v2",\n      "HistorianServerName ": "localhost",\n      "ApiClientId": "<deviceName>",\n      "ApiClientTimeoutSeconds": 300,\n      "FileSizeMegabytes": 8,\n      "AutoCreateDatasets": true,\n      "QueueDataPostRateMillis": 3000\n    },\n    "Auth": {\n      "UserName":"<USER-NAME>",\n      "UserPassword":"<USER-PASSWORD>"\n    }\n  }\n}\n')),(0,i.kt)("h3",{id:"general"},"General"),(0,i.kt)("p",null,"This section contains configuration fields which are used to configure the connector."),(0,i.kt)("h4",{id:"loglevel"},"LogLevel"),(0,i.kt)("p",null,"Parameter to configure the logging level used in the Ewon Flexy's realtime logs."),(0,i.kt)(l,{mdxType:"LoggingPartial"}),(0,i.kt)("h4",{id:"queueenablediagnostictags"},"QueueEnableDiagnosticTags"),(0,i.kt)("p",null,"Parameter to enable and monitor a set of diagnostic tags for the historical data queue.\nThese tags are automatically created and are used to monitor the health of the historical data queue\nby displaying a heartbeat with the number of times the queue has been accessed, a trigger to reset the\nqueue time tracker, and the number of seconds which the queue is running behind by."),(0,i.kt)("h4",{id:"queueenablestringhistory"},"QueueEnableStringHistory"),(0,i.kt)("p",null,"Parameter to configure if string history data should be retrieved from the queue.\nString history requires an additional EBD call in the underlying queue library, and will take extra\nprocessing time, especially in installations with large string tag counts."),(0,i.kt)("h4",{id:"utf8stringsupport"},"Utf8StringSupport"),(0,i.kt)("p",null,"Parameter to enable support for tag names with UTF-8 encoded characters."),(0,i.kt)("h4",{id:"queuedatapollsizemins"},"QueueDataPollSizeMins"),(0,i.kt)("p",null,"Parameter to configure the data poll size (in minutes) of each data queue poll.\nChanging this will modify the amount of data checked during each poll interval."),(0,i.kt)("h4",{id:"queuedatapollintervalmillis"},"QueueDataPollIntervalMillis"),(0,i.kt)("p",null,"Parameter to configure the maximum speed that the historical data be polled for new data points to send."),(0,i.kt)("h4",{id:"queuedatapollwarnbehindtimemins"},"QueueDataPollWarnBehindTimeMins"),(0,i.kt)("p",null,"Parameter to configure how many milliseconds the data queue will be able to fall behind real time before warnings are logged."),(0,i.kt)("h4",{id:"queuedatapollmaxbehindtimemins"},"QueueDataPollMaxBehindTimeMins"),(0,i.kt)("p",null,"Parameter to configure the data poll maximum behind time (in minutes).\nChanging this will modify the maximum number of minutes which the historical queue data polling may be running behind by."),(0,i.kt)("admonition",{type:"note"},(0,i.kt)("p",{parentName:"admonition"},"A value of -1 disables this functionality.")),(0,i.kt)("h3",{id:"canary"},"Canary"),(0,i.kt)("p",null,"This section contains configuration fields which are used to configure the connection to Canary."),(0,i.kt)("h4",{id:"api"},"API"),(0,i.kt)("p",null,"This section contains configuration fields which are used to configure the API connection."),(0,i.kt)("h5",{id:"url"},"Url"),(0,i.kt)("p",null,"Parameter to configure the URL in the following format:"),(0,i.kt)("pre",null,(0,i.kt)("code",{parentName:"pre"},'"https://<USER-DOMAIN>:<PORT_NUM>/"\n')),(0,i.kt)("h5",{id:"senderapiversionnumber"},"SenderApiVersionNumber"),(0,i.kt)("p",null,"Parameter to configure the sender API version number."),(0,i.kt)("h5",{id:"receiverapiversionnumber"},"ReceiverApiVersionNumber"),(0,i.kt)("p",null,"Parameter to configure the receiver API version number."),(0,i.kt)("h5",{id:"historianservername"},"HistorianServerName"),(0,i.kt)("p",null,"Parameter to configure the name for the historian(s) where data will be stored."),(0,i.kt)("admonition",{type:"note"},(0,i.kt)("p",{parentName:"admonition"},"If multiple historians are in use, names should be in a comma separated list.")),(0,i.kt)("h5",{id:"apiclientid"},"ApiClientId"),(0,i.kt)("p",null,"Parameter to configure the ID to be used as the dataset name for data coming from this device."),(0,i.kt)("admonition",{type:"note"},(0,i.kt)("p",{parentName:"admonition"},"It is recommended to use the device name as the ApiClientId.")),(0,i.kt)("h5",{id:"apiclienttimeoutseconds"},"ApiClientTimeoutSeconds"),(0,i.kt)("p",null,"Parameter to configure the timeout time in seconds for the API session token."),(0,i.kt)("admonition",{type:"note"},(0,i.kt)("p",{parentName:"admonition"},"It is recommended to use a large timeout to allow the session to be maintained."),(0,i.kt)("p",{parentName:"admonition"},"600 seconds would be an appropriate value.")),(0,i.kt)("h5",{id:"filesizemegabytes"},"FileSizeMegabytes"),(0,i.kt)("p",null,"Parameter to configure the number of megabytes a Canary Historical Database (HBD) file can grow to before the it rolls over."),(0,i.kt)("h5",{id:"autocreatedatasets"},"AutoCreateDatasets"),(0,i.kt)("p",null,"Parameter to configure if the historian should create new datasets for tags that specify\ndatasets that do not exist in the historian."),(0,i.kt)("h5",{id:"queuedatapostratemillis"},"QueueDataPostRateMillis"),(0,i.kt)("p",null,"Parameter to configure the maximum data post rate in milliseconds."),(0,i.kt)("h4",{id:"auth"},"Auth"),(0,i.kt)("p",null,"This section contains configuration fields which are used to configure the authentication."),(0,i.kt)("h5",{id:"username"},"UserName"),(0,i.kt)("p",null,"Parameter to configure the user name for authentication against the Canary API endpoint."),(0,i.kt)("admonition",{type:"note"},(0,i.kt)("p",{parentName:"admonition"},"This is a required parameter.")),(0,i.kt)("h5",{id:"userpassword"},"UserPassword"),(0,i.kt)("p",null,"Parameter to configure the user password for authentication against the Canary API endpoint."),(0,i.kt)("admonition",{type:"note"},(0,i.kt)("p",{parentName:"admonition"},"This is a required parameter.")))}g.isMDXComponent=!0}}]);