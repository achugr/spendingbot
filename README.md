# Telegram bot for spending tracking

**About:** You can start chat with this bot or add it into group chat and track money spending by simple commands.
This might be useful when you go somewhere with your friends and there is a lot of common bills paid by different people.
Spending tracking doesn't require internet connection, but total evaluation does.

**Currently available commands:**
+ /paid {SUM} - tracks that you spend {SUM} of money. {SUM} might be negative if you get some money for common bank
+ /total - shows total spend and how much everyone must get
+ /newsession \[SESSION NAME\] - creates new spend session, /total command returns sum in context of session

{} - required argument, [] - optional

**_IMPORTANT_**
+ Everyone, who take part in common bill, but didn't pay anything must type /paid 0
+ If someone from chat typed /newsession - new session will be created and currently you loose possibility to view previous total sum

**USE CASES**
Company of 3 people is travelling. If someone pay for others they must type /paid SUM in the chat. At the end of the trip (you need internet connection)
someone typed /total and you will see how much your company spend and how much money you and yours friends must get. If in the total bill you don't see someone -
it means that he didn't pay anything and he must type /paid 0. At the next trip you type /newsession.

**IMPROVEMENTS**
+ add custom keyboard for paid command
+ creating new session with specified people/person from chat (probably with custom keyboard)
+ possibility to join session
+ paid in specified session (for example, not all people want to visit cafe, in this case you’ll create new session for cafe and pay, then evaluate total in context of this session)
+ track payment for other user
+ total in specified session
+ paid in different currencies