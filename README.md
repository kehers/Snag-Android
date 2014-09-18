## What?
Snag is a spam filter for Android. The idea is to intelligently detect and block spam by matching content against trained data. You can read more about it here: <https://kehers.github.com/2014/09/07/building-an-sms-spam-filter.html>

## Status
Unfortunately, I have to stop further development. The way Snag should work is

1. Intercept incoming SMS
2. Match against trained data
3. If spam, save in a separate db and block from reaching the phone message inbox (or any other message app)

1 and 2 work already. However, from Android 4.4 (Kitkat), you can no longer block SMS from other messaging apps. [More details about this here](http://android-developers.blogspot.com/2013/10/getting-your-sms-apps-ready-for-kitkat.html). In other words, while Snag will work for Android versions < 4.4, it won't for >= 4.4 as not being able to stop messages from the message inbox defeats the purpose.

## What good is it then?
If you are interested in developing it for < 4.4, this source is a good headstart for you. I have done the heavy work already. The only things left to do are:

1. Create single message view. (The list view for all messages done already)
2. Allow users add a message to the training data (using the + icon)
3. Let users delete and "un-spam" a message
4. Add boring static content - about, feedback, rate

Another thing you can use this for is to create an app that listens to incoming messages to perform an action - OTP verification for example. Just strip away the spam classification part and use the SMSListener part.

## DB Schema
You will find the database in /assets/snag.db. It includes sample training data.

## Credits
Icon credits from [Noun Project](http://thenounproject.com)

## License
MIT