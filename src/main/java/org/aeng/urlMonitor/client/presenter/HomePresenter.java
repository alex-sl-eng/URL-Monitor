package org.aeng.urlMonitor.client.presenter;

import org.aeng.urlMonitor.client.NameTokens;
import org.aeng.urlMonitor.client.view.HomeUiHandlers;

import com.google.web.bindery.event.shared.EventBus;
import com.google.inject.Inject;

import com.gwtplatform.mvp.client.HasUiHandlers;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.Title;
import com.gwtplatform.mvp.client.proxy.PlaceManager;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealContentEvent;

public class HomePresenter extends Presenter<HomePresenter.MyView, HomePresenter.MyProxy> implements HomeUiHandlers
{
   /**
    * {@link HomePresenter}'s proxy.
    */
   @ProxyCodeSplit
   @NameToken(NameTokens.homePage)
   @Title("Home")
   public interface MyProxy extends ProxyPlace<HomePresenter>
   {
   }

   /**
    * {@link HomePresenter}'s view.
    */
   public interface MyView extends View, HasUiHandlers<HomeUiHandlers>
   {
   }

   private PlaceManager placeManager;

   @Inject
   public HomePresenter(final EventBus eventBus, final MyView view, final MyProxy proxy, final PlaceManager placeManager)
   {
      super(eventBus, view, proxy);
      this.placeManager = placeManager;
      view.setUiHandlers(this);
   }

   @Override
   protected void revealInParent()
   {
      RevealContentEvent.fire(this, BreadcrumbsPresenter.TYPE_SetMainContent, this);
   }

   @Override
   public void revealPublicJobsList()
   {
      placeManager.revealRelativePlace(new PlaceRequest(NameTokens.jobList).with(JobListPresenter.TOKEN_TYPE, JobListPresenter.TYPE_PUBLIC_JOBS));
   }

   @Override
   public void revealMyJobsList()
   {
      placeManager.revealRelativePlace(new PlaceRequest(NameTokens.jobList).with(JobListPresenter.TOKEN_TYPE, JobListPresenter.TYPE_MY_JOBS));
   }

}