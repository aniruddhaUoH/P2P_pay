## P2P_pay

P2P_pay is a payment portal. Using the platform you can send money to others who are on the same platform.

## Installation

Start with Wildfly, Webstorm, eclipse installed in ubuntu operating system.

1. First create a maven project using eclipse and import directory name as 'back-end'.
	#### You can get help from the link https://www.youtube.com/watch?v=BlkgrXb3L0c
2. Deploy the maven project in wildfly server.
	#### command to deploy:-
		mvn clean install; cp target/P2Ppay-0.0.1-SNAPSHOT.war ~/wildfly-10.1.0.Final/standalone/deployments/
3. Create a angular cli project using webstorm and import directory name as 'front-end'.
4. Publish website on server.

## Usage

So, how do you use this? What can it do?

1. User will have to login using OAuth service of facebook.

2. On the Home Page, there will be user's personal details. User can also edit details.

3. Under Load Money tab, user can load wallet by using .

