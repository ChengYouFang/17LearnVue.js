const SW = '[Service Worker]';
self.addEventListener('push', event => {
    console.log(`${SW} Push Received.`);
    console.log(`${SW} Push had this data: ${event.data.text()}`);
    // const message = JSON.parse(event.data.text())
    // const title = message.title
    // const options = {
    //     body: message.content
    // }
    // event.waitUntil(
    //     self.registration.showNotification(title, options)
    // )
    const title = '推送好消息';
    const options = {
        body: '好消息報報'
        // icon: '',
        // badge: ''
    };
    setTimeout(() => {
        event.waitUntil(self.registration.showNotification(title, options));
    }, 5000);
})


self.addEventListener('notificationclick', event => {
    console.log(`${SW} Notification click Received.`);
    event.notification.close();
    event.waitUntil(
        clients.openWindow(
            '
    https://developers.google.com/web/fundamentals/getting-   started/codelabs/push-notifications/'
        )
);
});