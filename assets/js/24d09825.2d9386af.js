(self.webpackChunkweb_docs=self.webpackChunkweb_docs||[]).push([[796],{3905:(e,t,n)=>{"use strict";n.d(t,{Zo:()=>u,kt:()=>m});var r=n(7294);function o(e,t,n){return t in e?Object.defineProperty(e,t,{value:n,enumerable:!0,configurable:!0,writable:!0}):e[t]=n,e}function a(e,t){var n=Object.keys(e);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(e);t&&(r=r.filter((function(t){return Object.getOwnPropertyDescriptor(e,t).enumerable}))),n.push.apply(n,r)}return n}function i(e){for(var t=1;t<arguments.length;t++){var n=null!=arguments[t]?arguments[t]:{};t%2?a(Object(n),!0).forEach((function(t){o(e,t,n[t])})):Object.getOwnPropertyDescriptors?Object.defineProperties(e,Object.getOwnPropertyDescriptors(n)):a(Object(n)).forEach((function(t){Object.defineProperty(e,t,Object.getOwnPropertyDescriptor(n,t))}))}return e}function c(e,t){if(null==e)return{};var n,r,o=function(e,t){if(null==e)return{};var n,r,o={},a=Object.keys(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||(o[n]=e[n]);return o}(e,t);if(Object.getOwnPropertySymbols){var a=Object.getOwnPropertySymbols(e);for(r=0;r<a.length;r++)n=a[r],t.indexOf(n)>=0||Object.prototype.propertyIsEnumerable.call(e,n)&&(o[n]=e[n])}return o}var l=r.createContext({}),s=function(e){var t=r.useContext(l),n=t;return e&&(n="function"==typeof e?e(t):i(i({},t),e)),n},u=function(e){var t=s(e.components);return r.createElement(l.Provider,{value:t},e.children)},d="mdxType",p={inlineCode:"code",wrapper:function(e){var t=e.children;return r.createElement(r.Fragment,{},t)}},f=r.forwardRef((function(e,t){var n=e.components,o=e.mdxType,a=e.originalType,l=e.parentName,u=c(e,["components","mdxType","originalType","parentName"]),d=s(n),f=o,m=d["".concat(l,".").concat(f)]||d[f]||p[f]||a;return n?r.createElement(m,i(i({ref:t},u),{},{components:n})):r.createElement(m,i({ref:t},u))}));function m(e,t){var n=arguments,o=t&&t.mdxType;if("string"==typeof e||o){var a=n.length,i=new Array(a);i[0]=f;var c={};for(var l in t)hasOwnProperty.call(t,l)&&(c[l]=t[l]);c.originalType=e,c[d]="string"==typeof e?e:o,i[1]=c;for(var s=2;s<a;s++)i[s]=n[s];return r.createElement.apply(null,i)}return r.createElement.apply(null,n)}f.displayName="MDXCreateElement"},8979:e=>{const t={repoOwnerName:"hms-networks",repoName:"flexy-canary-connector",title:"Flexy Canary Connector",description:"Synchronize Ewon Flexy data to Canary's Tag Historian using the Flexy Canary Connector.",meta:"Homepage for the HMS Networks Flexy Canary Connector."};e.exports={...t,repoUrl:"https://github.com/"+t.repoOwnerName+"/"+t.repoName,repoArchiveUrl:"https://github.com/"+t.repoOwnerName+"/"+t.repoName+"/archive/refs/heads/main.zip",repoLatestReleaseUrl:"https://github.com/"+t.repoOwnerName+"/"+t.repoName+"/releases/latest",repoNewIssueUrl:"https://github.com/"+t.repoOwnerName+"/"+t.repoName+"/issues/new"}},2552:(e,t,n)=>{"use strict";n.r(t),n.d(t,{assets:()=>p,contentTitle:()=>u,default:()=>h,frontMatter:()=>s,metadata:()=>d,toc:()=>f});var r=n(7462),o=n(7294),a=n(3905),i=n(6967),c=n(8979),l=n.n(c);const s={id:"introduction",title:"Introduction",sidebar_label:"Introduction",slug:"/"},u=void 0,d={unversionedId:"introduction",id:"introduction",title:"Introduction",description:"Description",source:"@site/docs/01-INTRODUCTION.mdx",sourceDirName:".",slug:"/",permalink:"/flexy-canary-connector/docs/",draft:!1,editUrl:"https://github.com/hms-networks/flexy-canary-connector/docs/01-INTRODUCTION.mdx",tags:[],version:"current",sidebarPosition:1,frontMatter:{id:"introduction",title:"Introduction",sidebar_label:"Introduction",slug:"/"},sidebar:"defaultSidebar",next:{title:"Change Log",permalink:"/flexy-canary-connector/docs/change-log"}},p={},f=[{value:"Description",id:"description",level:2},{value:"Getting Started",id:"getting-started",level:2},{value:"Features",id:"features",level:2},{value:"License",id:"license",level:2}],m={toc:f},y="wrapper";function h(e){let{components:t,...n}=e;return(0,a.kt)(y,(0,r.Z)({},m,n,{components:t,mdxType:"MDXLayout"}),(0,a.kt)("h2",{id:"description"},"Description"),(0,a.kt)(o.Fragment,null,"The ",l().title," is an application for synchronizing data from Ewon Flexy devices to Canary's Tag Historian using the Canary Sender API."),(0,a.kt)("br",null),(0,a.kt)("br",null),(0,a.kt)("admonition",{type:"note"},(0,a.kt)("p",{parentName:"admonition"},"The Sender Service is required to create an active session and store data points. This service must be\nenabled for a connection to be made.")),(0,a.kt)("h2",{id:"getting-started"},"Getting Started"),(0,a.kt)("p",null,"To get started, please refer to the ",(0,a.kt)("a",{parentName:"p",href:"/flexy-canary-connector/docs/quick-start-guide"},"Quick Start Guide"),". This guide\nprovides instructions for installing and configuring the connector, as well as, a brief overview of\nthe features and functionality provided by the connector."),(0,a.kt)(o.Fragment,null,"Additional information about the ",l().title," can be found on the various pages available in the menu to your left (above on mobile/small devices)."),(0,a.kt)("br",null),(0,a.kt)("h2",{id:"features"},"Features"),(0,a.kt)(i.ZP,{mdxType:"FeaturesPartial"}),(0,a.kt)("h2",{id:"license"},"License"),(0,a.kt)("p",null,"This project is licensed under the terms of the Apache 2.0 license. More information about the\nlicense and the full text of the license, can be found on the ",(0,a.kt)("a",{parentName:"p",href:"/flexy-canary-connector/docs/legal/license"},"LICENSE"),"\npage."))}h.isMDXComponent=!0},6967:(e,t,n)=>{"use strict";n.d(t,{ZP:()=>c});var r=n(7462),o=(n(7294),n(3905));const a={toc:[{value:"Synchronize Ewon Flexy Tag Data to Canary&#39;s Tag Historian",id:"synchronize-ewon-flexy-tag-data-to-canarys-tag-historian",level:3},{value:"Secure and Effective",id:"secure-and-effective",level:3}]},i="wrapper";function c(e){let{components:t,...n}=e;return(0,o.kt)(i,(0,r.Z)({},a,n,{components:t,mdxType:"MDXLayout"}),(0,o.kt)("h3",{id:"synchronize-ewon-flexy-tag-data-to-canarys-tag-historian"},"Synchronize Ewon Flexy Tag Data to Canary's Tag Historian"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Wide range of Ewon Flexy IO servers for various PLC connections"),(0,o.kt)("li",{parentName:"ul"},"Flexible tag configurations to tune historical data, alarming, and more"),(0,o.kt)("li",{parentName:"ul"},"Canary's high performance tag writes allows multiple connectors to send to a single historian if desired")),(0,o.kt)("h3",{id:"secure-and-effective"},"Secure and Effective"),(0,o.kt)("ul",null,(0,o.kt)("li",{parentName:"ul"},"Makes use of the Canary Sender API (v2)"),(0,o.kt)("li",{parentName:"ul"},"HTTPS Authentication using Certificates")))}c.isMDXComponent=!0}}]);