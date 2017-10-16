import { Angular2AuthenticationFirebasePage } from './app.po';

describe('angular2-authentication-firebase App', function() {
  let page: Angular2AuthenticationFirebasePage;

  beforeEach(() => {
    page = new Angular2AuthenticationFirebasePage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
