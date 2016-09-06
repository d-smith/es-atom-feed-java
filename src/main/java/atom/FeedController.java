package atom;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedController {
    @RequestMapping("/notifications/recent")
    public Feed recent() {
        Feed feed = new Feed();
        feed.setId("1");
        feed.setTitle("some stuffs");
        feed.setUpdated("today");

        return feed;
    }
}
