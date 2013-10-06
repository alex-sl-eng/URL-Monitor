var refreshPageInterval = 5000; //30 seconds
var refreshPageIntervalId = 0;
var contextPath;
var view = 'list';

$(document).ready(function() {
  $("#close_message_button").click(function() {
    $(".message").toggleClass("visible-message");
  });

  $("#list").click(function() {
    if (!$("#list").hasClass("selected")) {
      $('#list').addClass('selected');
    }
    $('#grid').removeClass('selected');
    $(".container").removeClass("grid");

    view = 'list';
  });

  $("#grid").click(function() {
    if (!$("#grid").hasClass("selected")) {
      $('#grid').addClass('selected');
    }
    $('#list').removeClass('selected');

    if (!$(".container").hasClass("grid")) {
      $(".container").addClass("grid");
    }

    view = 'grid';
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

      if (view == 'grid') {
        $('#grid').trigger('click');
      } else {
        $('#list').trigger('click');
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

        statusDiv.addClass('icon-close');
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