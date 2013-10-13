var refreshPageInterval = 30000; //30 seconds
var refreshPageIntervalId = 0;
var contextPath;
var VIEW_GRID= 'grid-view';
var VIEW_LIST = 'list-view';

var view = 'list-view';

$(document).ready(function() {
  $("#close_message_button").click(function() {
    $(".message").toggleClass("visible-message");
  });

  $("#list-view").click(function() {
    if (!$("#list-view").hasClass("selected")) {
      $('#list-view').addClass('selected');
    }
    $('#grid-view').removeClass('selected');
    $(".container").removeClass(VIEW_GRID);

    view = VIEW_LIST;
  });

  $("#grid-view").click(function() {
    if (!$("#grid-view").hasClass("selected")) {
      $('#grid-view').addClass('selected');
    }
    $('#list-view').removeClass('selected');

    if (!$(".container").hasClass(VIEW_GRID)) {
      $(".container").addClass(VIEW_GRID);
    }

    view = VIEW_GRID;
  });

  $("#auto_refresh").click(function() {
    if ($("#auto_refresh").is(':checked')) {
      refreshPageIntervalId = setInterval(refreshPage, refreshPageInterval);
    } else {
      clearInterval(refreshPageIntervalId);
    }
  });

  $("#filter_text").change(function() {
    var filterText = $('#filter_text').val();
    if (filterText) {
      //replace url here
    }
    $(".content").html(getLargeLoadingHtml());
    filterList(filterText);
  });
})

function filterList(filterText) {
  $.ajax({
    url : contextPath + '/filterList',
    cache : false,
    data : ({
      filterText : filterText
    }),
    success : function(response) {
      $(".content").html(response);

      if (view == VIEW_GRID) {
        $('#grid-view').trigger('click');
      } else {
        $('#list-view').trigger('click');
      }
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      clearInterval(refreshPageIntervalId);
      console.log(errorThrown);

      displayMessage('error', getErrorMessageHeader(), errorThrown);
    }
  });
}

function refreshPage() {
  $('#refresh_status').html(getLoadingHtml());

  $.ajax({
    url : contextPath + '/updateStatus',
    cache : false,
    success : function(data) {
      for ( var i = 0; i < data.length; i++) {
        var hashCode = data[i].hashCode;
        var status = data[i].status;

        var container = $('#' + hashCode + "-container");
        container.removeClass('Pass Failed Unknown')
        container.addClass(status);

        var statusDiv = container.find("span.status");
        statusDiv.removeClass('icon-checkmark icon-close icon-question');
        statusDiv.html('');

        if (status == 'Pass') {
          statusDiv.addClass('icon-checkmark');
        } else if (status == 'Failed') {
          statusDiv.addClass('icon-close');
        } else if (status == 'Unknown') {
          statusDiv.html(getLoadingHtml());
        }

        var lastCheckDiv = container.find("span.lastChecked");
        lastCheckDiv.html('');
        if (data[i].lastChecked) {
          lastCheckDiv.text(data[i].lastChecked);
        } else {
          lastCheckDiv.html(getLoadingHtml());
        }

        var lastFailedDiv = container.find("td.lastFailed");
        if (data[i].lastFailed) {
          lastFailedDiv.text(data[i].lastFailed);
        } else {
          lastFailedDiv.text("None");
        }
      }
      $('#refresh_status').html("");
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      clearInterval(refreshPageIntervalId);
      displayMessage('error', getErrorMessageHeader(), errorThrown);
      $('#refresh_status').html("");
    }
  });
}

function getLoadingHtml() {
  return "<img alt='Loading...' src='resources/images/loader.gif'/>";
}

function getLargeLoadingHtml() {
  return "<img alt='Loading...' src='resources/images/loader-large.gif'/>";
}

function getErrorMessageHeader() {
  return "Having problem communicating with server. "
      + "Try <a href='#' onclick='location.reload(true); return false;'>refresh page</a> later.";
}

function displayMessage(severity, messageHeaderHTML, message) {
  if (messageHeaderHTML) {
    clearSeverityMessage();
    if (!$(".message").hasClass("visible-message")) {
      $(".message").addClass("visible-message");
      $(".message").addClass(severity);
    }
    $("#message").html(messageHeaderHTML);

    $('#message_details').text(message);
  }
}

function clearSeverityMessage() {
  $(".message").removeClass("info");
  $(".message").removeClass("warning");
  $(".message").removeClass("error");
}

function toggleDetails(toggleBtn, rowId) {
  var details = $('#' + rowId + "-container").find("div.details");
  details.toggleClass("visible");
  $(toggleBtn).toggleClass("rotated");
}

function validateName(name) {
  $.ajax({
    type : "POST",
    url : contextPath + "/validateName",
    data : "name=" + name,
    success : function(response) {
      if (response.status == "Failed") {
        $('#name_error').html(response.result);
        $('#name_error').addClass("l--display-inline-block")
      } else {
        $('#name_error').html('');
        $('#name_error').removeClass("l--display-inline-block")
      }
    },
    error : function(XMLHttpRequest, textStatus, errorThrown) {
      displayMessage('error', getErrorMessageHeader(), errorThrown);
      $('#refresh_status').html("");
    }
  });
}