#!/usr/bin/env ruby
require 'bunny'

connection = Bunny.new
connection.start

channel = connection.create_channel
queue = channel.queue('task_queue', durable: true)

channel.prefetch(1)
puts ' [*] Waiting for messages. To exist press CTRL+C'

begin
    queue.subscribe(manual_ack:true, block: true) do |delivery_info, _properties, body|
      puts " [x] Received #{body}"
      sleep body.count('.').to_i
      puts ' [x] Done'
      channel.ack(delivery_info.delivery_tag)
    end
rescue Interrupt => _
    puts " ... Cerrando conexion"
    connection.close
    exit(0)
end